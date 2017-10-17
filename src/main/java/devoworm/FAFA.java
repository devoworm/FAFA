package devoworm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

@Path("/servlet")
public class FAFA
{
   // PubMed address.
   public static final String PUBMED_ADDRESS = "https://www.ncbi.nlm.nih.gov/pubmed/?term=";

   @Path("/address_query")
   @POST
   @Produces("text/html")
   public String addressQuery(@FormParam ("email") String email)
   {
      String text = "";

      ArrayList<String> textLines = new ArrayList<String>();
      try
      {
         ClientRequest request = new ClientRequest(PUBMED_ADDRESS + URLEncoder.encode(email, "UTF-8"));
         request.accept("text/html");
         ClientResponse<String> response = request.get(String.class );

         if (response.getStatus() != 200)
         {
            return("Error querying PubMed: " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(
                                                   new ByteArrayInputStream(response.getEntity().getBytes())));

         String line;
         while ((line = br.readLine()) != null)
         {
            textLines.add(line);
            text += line + "\n";
         }
      }
      catch (Exception e)
      {
         return("Error querying PubMed: " + e.getMessage());
      }

      String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
      AbstractSequenceClassifier<CoreLabel> classifier = null;
      try
      {
         classifier = CRFClassifier.getClassifier(serializedClassifier);
      }
      catch (Exception e)
      {
         return("Error getting Stanford NLP classifier: " + e.getMessage());
      }

      /*
      for (String str : textLines)
      {
         System.out.println(classifier.classifyToString(str));
      }
      System.out.println("---");

      for (String str : textLines)
      {
         // This one puts in spaces and newlines between tokens, so just print not println.
         System.out.print(classifier.classifyToString(str, "slashTags", false));
      }
      System.out.println("---");

      for (String str : textLines)
      {
         // This one is best for dealing with the output as a TSV (tab-separated column) file.
         // The first column gives entities, the second their classes, and the third the remaining text in a document
         System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
      }
      System.out.println("---");
      */

      for (String str : textLines)
      {
         System.out.println(classifier.classifyWithInlineXML(str));
      }
      System.out.println("---");

      /*
      for (String str : textLines)
      {
         System.out.println(classifier.classifyToString(str, "xml", true));
      }
      System.out.println("---");

      for (String str : textLines)
      {
         System.out.print(classifier.classifyToString(str, "tsv", false));
      }
      System.out.println("---");

      // This gets out entities with character offsets
      int j = 0;
      for (String str : textLines)
      {
         j++;
         List < Triple < String, Integer, Integer >> triples = classifier.classifyToCharacterOffsets(str);
         for (Triple<String, Integer, Integer> trip : triples)
         {
            System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
                              trip.first(), trip.second(), trip.third, j);
         }
      }
      System.out.println("---");

      // This prints out all the details of what is stored for each token
      int i = 0;
      for (String str : textLines)
      {
         for (List<CoreLabel> lcl : classifier.classify(str))
         {
            for (CoreLabel cl : lcl)
            {
               System.out.print(i++ + ": ");
               System.out.println(cl.toShorterString());
            }
         }
      }
      */

      return("<html><body><h1>PubMed query results for email: " + email + "</h1><p><pre>" + text + "</pre></body></html>");
   }
}
