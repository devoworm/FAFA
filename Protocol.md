### Needs to be automated:

1) Start with the person's name and an old email address.

2) Search PubMed, public version, for the old email address.

3) Make a list LIST1 of all keywords from the references REF1 that contain that email address. Keywords include all text, i.e., abstract, addresses, names of coauthors.

4) Search on the name in PubMed:

* take the subset not containing the old email address, REF2, that was published after the latest use of the old, defunct email address,

* take the subset not containing the old email address, REF3, that was published before the latest use of the old, defunct email address,

5) Remove any reference in REF2 that has an email address appearing in REF3. Call the result REF4.

6) Rank the results in REF4 that have different email addresses according to overlaps of their words with LIST1.

7) Extract the email addresses from REF4.

8) Make a list LIST2 with new email addresses and ranks of the references in which they were found.

* Reject any email addresses in LIST2 that are not valid.

* Rank the new email addresses according to similarity with the old one.

9) Use the (weighted) two ranks to do best 1st, second and third guesses for the person's new email address.

The Pubmed File output format Abstract produces all the information we'd need for this protocol. The list is in reverse chronological order. See the attached sample, file "Abstract_pubmed_result.txt", which includes my old email addresses gordonr@cc.umanitoba.ca and GordonR@ms.UManitoba.ca, and an erroneous gordonr@ce.manitoba.ca (probably a scanning error). The result should be my new email address: dickgordoncan@gmail.com.
