# homework 1
# goal: tokenize, index, boolean query
# exports: 
#   student - a populated and instantiated ir4320.Student object
#   Index - a class which encapsulates the necessary logic for
#     indexing and searching a corpus of text documents


# ########################################
# first, create a student object
# ########################################

import glob
import re
import ir4320
import PorterStemmer
from tkinter import *
import sys

MY_NAME = "Garrett"
MY_ANUM  = 0 # My a# is a01805667
MY_EMAIL = "bogartgarrett@gmail.com"

# the COLLABORATORS list contains tuples of 2 items, the name of the helper
# and their contribution to your homework
COLLABORATORS = [ 
    ('Daniel Oliveros ', 'told me about isalnum() which helped with tokenization'),
    ('Brown Cheng', 'gave me coffee during office hours'),
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
# an index created from a directory of text files 
class Index(object):
    def __init__(self):
        # _inverted_index contains terms as keys, with the values as a list of
        # document indexes containing that term
        self._inverted_index = {}
        # _documents contains file names of documents
        self._documents = []
        # example:
        #   given the following documents:
        #     doc1 = "the dog ran"
        #     doc2 = "the cat slept"
        #   _documents = ['doc1', 'doc2']
        #   _inverted_index = {
        #      'the': [0,1],
        #      'dog': [0],
        #      'ran': [0],
        #      'cat': [1],
        #      'slept': [1]
        #      }


    # index_dir( base_path )
    # purpose: crawl through a nested directory of text files and generate an
    #   inverted index of the contents
    # preconditions: none
    # returns: num of documents indexed
    # hint: glob.glob()
    # parameters:
    #   base_path - a string containing a relative or direct path to a
    #     directory of text files to be indexed
    def checkDictionary(self, index, num):#determines if a word from the current document already is in the dictionary
        found = False
        #print(index, num)
        for document in index:
            if(document == num):
                found = True
            else:
                found = False
        return found

    def index_dir(self, base_path):
        num_files_indexed = 0
        #tokens = []
        # PUT YOUR CODE HERE
        for file in glob.glob(base_path+"*"):
            tokens = []
            num_files_indexed +=1
            #print(file)
            fileReader = open(file, "r", encoding = "utf8")
            data = fileReader.read().split()
            #print (data)
            tokens = self.tokenize(data)+tokens
            stemmed_tokens = self.stemming(tokens)
            #print(stemmed_tokens)
            for word in stemmed_tokens:
                #print(self._inverted_index)
                if(self._inverted_index.__contains__(word) != True):#word wasn't in the dictionary
                    self._inverted_index[word] = [num_files_indexed]
                else:#word is in dictionary
                    found = self.checkDictionary(self._inverted_index[word],num_files_indexed)
                    if( found == False):#if the value is from a different document then add it
                        self._inverted_index[word] = self._inverted_index[word] + [num_files_indexed]
        print(self._inverted_index)
        return num_files_indexed

    # tokenize( text )
    # purpose: convert a string of terms into a list of tokens.        
    # convert the string of terms in text to lower case and replace each character in text, 
    # which is not an English alphabet (a-z) and a numerical digit (0-9), with whitespace.
    # preconditions: none
    # returns: list of tokens contained within the text
    # parameters:
    #   text - a string of terms
    def tokenize(self, text):
        tokens = []
        # PUT YOUR CODE HERE
        for word in text:
            if(word.isalnum()):
                tokens.append(str.casefold(word))
            else:
                for letter in word:
                    if(letter.isalnum() == False):
                        word = word.replace(letter,'')
                tokens.append(str.casefold(word))
        return tokens

    # purpose: convert a string of terms into a list of tokens.        
    # convert a list of tokens to a list of stemmed tokens,     
    # preconditions: tokenize a string of terms
    # returns: list of stemmed tokens
    # parameters:
    #   tokens - a list of tokens
    def stemming(self, tokens):
        stemmed_tokens = []
        PS = PorterStemmer.PorterStemmer()
        for word in tokens:
            stemmed_tokens.append(PS.stem(word, 0, len(word)-1))
        # PUT YOUR CODE HERE
        #print(stemmed_tokens)
        return stemmed_tokens
    
    # boolean_search( text )
    # purpose: searches for the terms in "text" in our corpus using logical OR or logical AND. 
    # If "text" contains only single term, search it from the inverted index. If "text" contains three terms including "or" or "and", 
    # do OR or AND search depending on the second term ("or" or "and") in the "text".  
    # preconditions: _inverted_index and _documents have been populated from
    #   the corpus.
    # returns: list of document names containing relevant search results
    # parameters:
    #   text - a string of terms
    def boolean_search(self, text):
        results = []
        count = 0
        # PUT YOUR CODE HERE
        PS = PorterStemmer.PorterStemmer()
        stemmed_Search =[]
        words = text.split()
        for word in words:
            stemmed_Search.append(PS.stem(word, 0, len(word)-1))
            #print(stemmed_Search)
        for word in words:
            if(re.search('AND', word)):#if AND is in the query
                results+=self.computeAND(words[count-1], words[count])
                return results
            if(re.search('OR', word)):#if OR is in the query
                #print(words[count - 1])
                #print(words[count])
                results += self.computeOR(words[count-1], words[count])
                return results

        if(self._inverted_index.__contains__(stemmed_Search[0])):#for the cases without and/or
            #print(stemmed_Search[0])
            #print(self._inverted_index['footbal'])
            results = self._inverted_index[stemmed_Search[0]]

        return results

    def computeOR(self,word1, word2):
        results = []
        results1 =[]
        results2 = []
        if (self._inverted_index.__contains__(word1)):
            results1 = self._inverted_index[word1]
        if (self._inverted_index.__contains__(word2)):
            results2 = self._inverted_index[word2]
        results = results1
        for rez in results2:
            if(rez not in results):
                results.append(rez)
        return results

    def computeAND(self, word1, word2):
        results =[]
        resultsWord1 = []
        resultsWord2 = []
        if(self._inverted_index.__contains__(word1)):
            resultsWord1 = self._inverted_index[word1]
        if (self._inverted_index.__contains__(word2)):
            resultsWord2 = self._inverted_index[word2]
        if(len(resultsWord1)>=len(resultsWord2)):
            for docs in resultsWord1:
                for doc in resultsWord2:
                    if(docs == doc):
                        results.append(docs)
                        continue
        else:
            for docs in resultsWord2:
                for doc in resultsWord1:
                    if (docs == doc):
                        results.append(docs)
                        continue
        #print(results)
        return results
# now, we'll define our main function which actually starts the indexer and
# does a few queries

def convertToStringFormat(ListofInt):
    results = ""
    for int in ListofInt:
        results+=str(int)
    return results

def main2(args):
    temp = []
    print(student)
    index = Index()
    print("starting indexer")
    num_files = index.index_dir('data2/')
    print("indexed %d files" % num_files)
    #for term in ('football', 'mike', 'sherman', 'mike OR sherman','mike AND sherman'):
        #results = index.boolean_search(term)
        #results = convertToStringFormat(results)
        #temp.append((term, ", ".join(results)))
        #print(results)
    results = index.boolean_search(args)
    results = convertToStringFormat(results)
    temp.append((args, ", ".join(results)))
    return temp


def print_output():
    search = search_query.get()
    results = main2(search)
    label2 = Label(top, text = results).place(x = 0, y = 30)

top = Tk()
search_query = StringVar()

top.geometry('450x450')
top.title("Yelping Yelpers")

Button1 = Button(top,text = 'search', command = print_output).place(x = 0, y = 0,width = 120, height = 25)
Entry1 = Entry(top,textvariable = search_query).place(x = 130, y = 0,width = 120, height = 25)
top.mainloop()




