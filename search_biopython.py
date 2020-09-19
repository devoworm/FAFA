# you need to install Biopython:
# pip install biopython

# Full discussion:
# https://marcobonzanini.wordpress.com/2015/01/12/searching-pubmed-with-python/

from Bio import Entrez

def search(query):
    Entrez.email = 'portegys@gmail.com'
    handle = Entrez.esearch(db='pubmed', 
                            sort='relevance', 
                            retmax='20',
                            retmode='xml',
                            term=query)
    results = Entrez.read(handle)
    return results

def fetch_details(id_list):
    ids = ','.join(id_list)
    Entrez.email = 'portegys@gmail.com'
    handle = Entrez.efetch(db='pubmed',
                           retmode='xml',
                           id=ids)
    results = Entrez.read(handle)
    return results

if __name__ == '__main__':
    results = search('LSTM')
    id_list = results['IdList']
    print(id_list)
    papers = fetch_details(id_list)
    print(papers)
    for paper in enumerate(papers):
        print(paper[1])
    #for i, paper in enumerate(papers):
        #print("%d) %s" % (i+1, paper['MedlineCitation']['Article']['ArticleTitle']))
    # Pretty print the first paper in full
    #import json
    #print(json.dumps(papers[0], indent=2, separators=(',', ':')))
