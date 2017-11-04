# homework 4
# goal: k-means clustering on vectors of TF-IDF values,
#   normalized for every document.
# exports: 
#   student - a populated and instantiated ir470.Student object
#   Clustering - a class which encapsulates the necessary logic for
#       clustering a set of documents by tf-idf 


# ########################################
# first, create a student object
# ########################################
import numpy as np
from random import shuffle
from pathlib import Path
import glob
import ir4320
#import re
MY_NAME = "Daniel Oliveros"
MY_ANUM  = 00000000 # put your UID here
MY_EMAIL = "daniel.oliveros@aggiemail.usu.edu"



# the COLLABORATORS list contains tuples of 2 items, the name of the helper
# and their contribution to your homework
COLLABORATORS = [
    ('Garrett Bogart', 'Helped me figure out how a bunch of the code works'),
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

class Cluster(object):

    def __init__(self, vector):
        self.numVectors = 0
        self.vectors = []
        self.numVectors = 1
        self.centroid = vector[1]
        self.vectors.append(vector)

    def addVector(self, vector):
        #print (type(vector))
        #print (vector)
        if self.getDistanceFromCentroid(vector[1])==0:
            if self.numVectors == 1:
                return
        self.numVectors += 1
        self.vectors.append(vector)

    def calculateCentroid(self):
        if self.numVectors == 0:
            return self.centroid
        datBoi = np.copy(self.vectors[0][1])
        for x in range(1, self.numVectors):
            datBoi+= self.vectors[x][1]
        resultKid = datBoi/self.numVectors
        return resultKid

    def getDistanceFromCentroid(self, vector):
        dist = 0
        #print (type(self.centroid))
        #print (type(vector))
        #print (self.centroid[0])
        #print(vector[0])
        for i in range (0, len(vector)):
            temp = (self.centroid[i] - vector[i])**2
            dist += temp
        #dist = np.linalg.norm(np.array(self.centroid) - np.array(vector))
        return (dist**0.5)

    def getRSS(self):
        distance = 0
        for vector in self.vectors:
            tempD = self.getDistanceFromCentroid(vector[1])
            distance += tempD
        return distance

    def allDistancesFromCentroid(self, vectors):
        distances = []
        for vector in vectors:
            tempD = self.getDistanceFromCentroid(vector[1])
            distances.append(tempD)
        return distances

    def printCentroid(self):
        print (self.centroid)

    def getCentroid(self):
        return self.centroid

    def printVectors(self):
        print (self.vectors)

    def getVectors(self):
        return self.vectors

    def removeVectors(self):
        self.centroid = self.calculateCentroid()
        self.vectors = []
        self.numVectors = 0

# Our Clustering object will contain all logic necessary to crawl a local
# directory of text files, tokenize them, calculate tf-idf vectors on their
# contents then cluster them according to k-means. The Clustering class should
# select r random restarts to ensure getting good clusters and then use RSS, an
# internal metric, to calculate best clusters.  The details are left to the
# student.

class Clustering(object):
    # hint: create something here to hold your dictionary and tf-idf for every
    #   term in every document
    def __init__(self):
        self.filenames = []
        self._inverted_index = {}
        # _documents contains file names of documents
        self._documents = []
        self._Incidence_vectors = []
        self._numDocs = 0

    # tokenize( text )
    # purpose: convert a string of terms into a list of terms 
    # preconditions: none
    # returns: list of terms contained within the text
    # parameters:
    #   text - a string of terms
    def tokenize(self, text):
        tokens = []
        for word in text:
            i = 0
            for char in word:
                if not str.isalnum(char):
                    word = word[:i] + ' ' + word[i+1:]
                i += 1
            lowercase = word.casefold()
            spaceless = "".join(str.split(lowercase))
            tokens.append(spaceless)
        #print(tokens)
        return tokens

    def index_dir(self, base_path):
        num_files_indexed = 0
        tokened_terms = []
        fileIndex = 0;
        for file in glob.glob(base_path + "*"):
            #print (file)
            n = Path(file)
            #print(n.parts[1])
            self._documents.append(n.parts[1])
            self._numDocs += 1
            fileIndex+=1
            num_files_indexed += 1
            fileThing = open(file, "r",encoding="utf-8")
            gatheredString = fileThing.read()
            #print("Tokenizing")
            splitStr = gatheredString.split()
            tokened = self.tokenize(splitStr)
            for word in tokened:
                if not self._inverted_index.__contains__(word):
                    #self._inverted_index[word] = fileIndex
                    self._inverted_index.setdefault(word, []).append(fileIndex)
                    #print(self._inverted_index[word])
                else:
                    found = False
                    for doc in self._inverted_index[word]:
                        if doc == fileIndex:
                            found = True
                    self._inverted_index[word].append(fileIndex)
                        # PUT YOUR CODE HERE
        #print(self._inverted_index)
        return num_files_indexed


    def doTF_IDF(self):
        for key in self._inverted_index:
            passedlist = []
            #print (passedlist)
            d = {x: self._inverted_index[key].count(x) for x in self._inverted_index[key]}
            #print(d)
            for i in range(0, self._numDocs):
                passedlist.append(0)
            for element in d:
                #print (d[element])
                passedlist[element-1]=d[element]
            #print(passedlist)
            DF = 0
            for element in d:
                DF += 1
            IDF = np.log10(self._numDocs / DF)
            #print(passedlist)
            if not self._Incidence_vectors:
                for i in passedlist:
                    if i != 0:
                        TF = 1+np.log10(i)
                        temp = [TF*IDF]
                        self._Incidence_vectors.append(temp)
                    else:
                        temp = [i]
                        self._Incidence_vectors.append(temp)
            else:
                for i in range(0, len(passedlist)):
                    if passedlist[i] != 0:
                        TF = 1 + np.log10(passedlist[i])
                        self._Incidence_vectors[i].append(TF * IDF)
                    else:
                        self._Incidence_vectors[i].append(passedlist[i])
        #print(self._Incidence_vectors)


    # consume_dir( path, k )
    # purpose: accept a path to a directory of files which need to be clustered
    # preconditions: none
    # returns: list of documents, clustered into k clusters
    #   structured as follows:
    #   [
    #       [ first, cluster, of, docs, ],
    #       [ second, cluster, of, docs, ],
    #       ...
    #   ]
    #   each cluster list above should contain the document name WITHOUT the
    #   preceding path.  JUST The Filename.
    # parameters:
    #   path - string path to directory of documents to cluster
    #   k - number of clusters to generate
    def consume_dir(self, path, k):
        self.__init__()
        self.index_dir(path)
        self.doTF_IDF()
        clusters = self.clusterStuff(k)
        listOfClusters = []
        for i in clusters:
            temp = []
            for r in range(0, i.numVectors):
                temp.append(self._documents[i.vectors[r][0]])
            listOfClusters.append(temp)

        #print(self._documents)
        #print(self._inverted_index)

        return listOfClusters

    def clusterStuff(self,k):
        docList = []
        for i in range(0, self._numDocs):
            docList.append(i)
        shuffle(docList)
        clusterList = []
        for i in range(0, k):
            #print (docList[i])
            clusterList.append(Cluster((docList[i],np.array(self._Incidence_vectors[docList[i]]))))

        addList =[]
        for i in docList:
            distances= []
            index = 0
            for j in clusterList:
                tempPair = (index, (i, j.getDistanceFromCentroid(np.array(self._Incidence_vectors[i]))))
                distances.append(tempPair)
                index+=1
            #print(distances)
            sortedDs = sorted(distances, key = lambda distances:distances[1])
            #print (sortedDs)
            #print (sortedDs[0][0])
            addList.append(sortedDs[0])

        #print(addList)
        for i in range(0,k):
            #print(clusterList[i].numVectors)
            clusterList[i].removeVectors()
            #print(clusterList[i].vectors[i])
        #print("damn")
        index = 0
        for i in addList:
            clusterList[i[0]].addVector((docList[index], np.array(self._Incidence_vectors[docList[index]])))
            index+=1
        #for i in range(0,k):
            #print(clusterList[i].numVectors)
            #print(clusterList[i].vectors[i])
        totalRSS = 0
        #print(type(clusterList))
        #print(type(totalRSS))
        for i in range(0, k):
            totalRSS += clusterList[i].getRSS()
        newRSS = totalRSS / 2
        dummy = 0
        while totalRSS-newRSS > totalRSS*0.1:
            if dummy != 0:
                totalRSS = newRSS
                dummy +=1
            else:
                dummy+=1
            for i in range(0, k):
                clusterList[i].removeVectors()
            addList = []
            for i in docList:
                distances = []
                index = 0
                for j in clusterList:
                    tempPair = (index, (i, j.getDistanceFromCentroid(self._Incidence_vectors[i])))
                    distances.append(tempPair)
                    index += 1
                # print(distances)
                sortedDs = sorted(distances, key=lambda distances: distances[1])
                # print (sortedDs)
                # print (sortedDs[0][0])
                addList.append(sortedDs[0])
            #print(addList)
            index = 0
            for i in addList:
                clusterList[i[0]].addVector((docList[index], np.array(self._Incidence_vectors[docList[index]])))
                index += 1
            #for i in range(0, k):
                #print(clusterList[i].numVectors)
            newRSS = 0
            for i in range(0, k):
                newRSS += clusterList[i].getRSS()
        #print (dummy)
        return clusterList

# now, we'll define our main function which actually starts the clusterer
def main(args):
    print(student)
    clustering = Clustering()
    for i in range(0,10):
        print("test 10 documents")
        print(clustering.consume_dir('test10/', 5))
    for i in range (0,50):
        print("test 50 documents")
        print(clustering.consume_dir('test50/', 3))

    #print("test 5 documents")
    #print(clustering.consume_dir('test5/', 2))

# this little helper will call main() if this file is executed from the command
# line but not call main() if this file is included as a module
if __name__ == "__main__":
    import sys
    main(sys.argv)
