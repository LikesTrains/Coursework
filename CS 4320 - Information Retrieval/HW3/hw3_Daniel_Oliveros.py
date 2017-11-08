# homework 4
# goal: ranked retrieval, PageRank, crawling
# exports:
#   student - a populated and instantiated ir470.Student object
#   PageRankIndex - a class which encapsulates the necessary logic for
#     indexing and searching a corpus of text documents and providing a
#     ranked result set

# ########################################
# first, create a student object
# ########################################

import ir4320
import urllib.request
import numpy as numpy

import bs4 as bs  # you will want this for parsing html documents

MY_NAME = "Daniel Oliveros"
MY_ANUM  = 0000000 # put your UID here
MY_EMAIL = "daniel.oliveros@aggiemail.usu.edu"

# the COLLABORATORS list contains tuples of 2 items, the name of the helper
# and their contribution to your homework
COLLABORATORS = [ 
    ('Garrett Bogart', 'Guided me through some of the code'),
    ]

# Set the I_AGREE_HONOR_CODE to True if you agree with the following statement
# "An Aggie does not lie, cheat or steal, or tolerate those who do."
I_AGREE_HONOR_CODE = True

# this defines the student object
student = ir4320.Student(
    MY_NAME,
    MY_ANUM,
    MY_EMAIL,
    COLLABORATORS,
    I_AGREE_HONOR_CODE
    )


# ########################################
# now, write some code
# ########################################




# our index class definition will hold all logic necessary to create and search
# an index created from a web directory
#
# NOTE - if you would like to subclass your original Index class from homework
# 1 or 2, feel free, but it's not required.  The grading criteria will be to
# call the index_url(...) and ranked_search(...) functions and to examine their
# output.  The index_url(...) function will also be examined to ensure you are
# building the index sanely.

class PageRankIndex(object):
    def __init__(self):
        # you'll want to create something here to hold your index, and other
        # necessary data members
        self.urls = []
        self.teleporting_matrix = []
        self.trans_matrix = []
        self.inv_index = {}
        self.stochastic_matrix = []
        self.asymptotic_behavior = []
        self.weight = .1
        pass

    def make_transMatrix(self):
        for url in self.urls:
            source = urllib.request.urlopen(url).read()
            souperino = bs.BeautifulSoup(source, "html.parser")

            urls =[]

            for link in souperino.find_all('a'):
                linkerino = (link.get('href'))
                urls.append(linkerino)


            linksFound = []
            numIndexes = 0
            for link in urls:
                numIndexes+=1
                for word in link:
                    if word.isnumeric():
                        linksFound.append(int(word))
            index = 0
            trans_Row =[]
            while(index<len(self.urls)):
                if index in linksFound:
                   trans_Row.append(1/numIndexes)
                else:
                    trans_Row.append(0)
                index+=1
            #print(trans_Row)
            self.trans_matrix.append(trans_Row)
        return 0

    def make_teleportingMatrix(self):
        length = len(self.urls)
        prob = 1/length

        row = []
        for index in range (0, length):
            row.append(prob)

        for index in range(0, length):
            self.teleporting_matrix.append(row)

    def make_stochasticMatrix(self):
        length = len(self.urls)
        for i in range (0, length):
            row =[]
            for j in range (0, length):
                row.append((self.weight*self.teleporting_matrix[i][j]) + ((1-self.weight)*self.trans_matrix[i][j]))
            self.stochastic_matrix.append(row)

    def checkIfConverged(self, now, next):
        error = 0.01
        done = True
        for i in range (0, len(now)):
            if abs(now[i]-next[i])>error:
                done = False
        return done

    def calculateAsymptoticBehavior(self):
        x_now = []
        x_next = []
        x_next.append(1)
        for i in range (0, len(self.stochastic_matrix)):
            x_now.append(0)
            if i > 0:
                x_next.append(0)
        done = False
        while (done == False):
            x_now = x_next
            x_next = numpy.dot(x_now, self.stochastic_matrix)
            done = self.checkIfConverged(x_now, x_next)
        self.asymptotic_behavior = x_now


    # retrieve_urls( url)
    # purpose: retieve all urls found in a given page url
    def retrieve_urls(self, url):
        source = urllib.request.urlopen(url).read()
        souperino = bs.BeautifulSoup(source, "html.parser")
        numDocs = 0;

        for link in souperino.find_all('a'):
            linkerino = (link.get('href'))
            self.urls.append(url.replace('index.html', linkerino))

        #print(self.urls)

        for url in self.urls:
            numDocs += 1
        return numDocs



    def doMatrices(self):
        self.make_transMatrix()
        #print (self.trans_matrix)
        self.make_teleportingMatrix()
        #print (self.teleporting_matrix)
        self.make_stochasticMatrix()
        #print (self.stochastic_matrix)
        self.calculateAsymptoticBehavior()
        #print(self.asymptotic_behavior)
        return 0



    def buildIndex(self):
        for url in self.urls:
            src = urllib.request.urlopen(url).read()
            sopa = bs.BeautifulSoup(src, 'lxml')

            for paragraph in sopa.find_all('body'):
                contents = paragraph.text
                # print (contents)
                tokens = self.tokenize(contents)
                for word in tokens:
                    if word not in self.inv_index:
                        self.inv_index[word] = []  # create new key in dictionary with url
                    if word in self.inv_index:
                        if url not in self.inv_index[word]:
                            self.inv_index[word] += [url]
        return 0


    # index_url( url )
    # purpose: crawl through a web directory of html files and generate an
    #   index of the contents
    # preconditions: none
    # returns: num of documents indexed
    # hint: BeautifulSoup and urllib2 s
    # parameters:
    #   url - a string containing a url to begin indexing at
    def index_url(self, url):
        numDocs = self.retrieve_urls(url) #initializes list of URLs, returns the number of Documents indexed
        self.doMatrices()
        self.buildIndex()
        #print(self.inv_index)
        #print(numDocs)
        # ADD CODE HERE
        return numDocs

    # tokenize( text )
    # purpose: convert a string of terms into a list of terms 
    # preconditions: none
    # returns: list of terms contained within the text
    # parameters:
    #   text - a string of terms
    def tokenize(self, text):
        import re
        clean_string = re.sub('[^a-z0-9 ]', ' ', text.lower())
        tokens = clean_string.split()
        return tokens

    def getSet(self, word):
        returned = set()
        if word in self.inv_index:
            docs = self.inv_index[word]
            for link in docs:
                temp = link.replace('http://digital.cs.usu.edu/~kyumin/cs4320/new10/', '')
                for letters in temp:
                    if letters.isnumeric():
                        returned.add(int(letters))
        return returned

    def makePairs(self, res, behavior):
        pairs = []
        for value in res:
            pair = []
            pair.append(value)
            pair.append(behavior[value])
            pairs.append(pair)
        return pairs
    # ranked_search( text )
    # purpose: searches for the terms in "text" in our index and returns
    #   AND results for highest 10 ranked results
    # preconditions: .index_url(...) has been called on our corpus
    # returns: list of tuples of (url,PageRank) containing relevant
    #   search results
    # parameters:
    #   text - a string of query terms
    def ranked_search(self, text):

        words = self.tokenize(text)
        setA = self.getSet(words[0])

        for term in words:
            setA = setA.intersection(self.getSet(term))

        results = list(setA)
        #print(results)

        sorting = self.makePairs(results, self.asymptotic_behavior)
        #print(sorting)
        sorting.sort(key=lambda pair: pair[1], reverse=True)

        final = []

        for i in range (0, len(sorting)):
            if i>10:
                return final

            final.append(sorting[i])

        if len(setA) == 0:
            return "None"

        return final


# now, we'll define our main function which actually starts the indexer and
# does a few queries
def main(args):
    print(student)
    index = PageRankIndex()
    url = 'http://digital.cs.usu.edu/~kyumin/cs4320/new10/index.html'
    num_files = index.index_url(url)
    search_queries = (
       'palatial', 'college ', 'palatial college', 'college supermarket', 'famous aggie supermarket'
        )
    for q in search_queries:
        results = index.ranked_search(q)
        print("searching: %s -- results: %s" % (q, results))


# this little helper will call main() if this file is executed from the command
# line but not call main() if this file is included as a module
if __name__ == "__main__":
    import sys
    main(sys.argv)

