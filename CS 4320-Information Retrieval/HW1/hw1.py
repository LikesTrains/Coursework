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
import ir4320
import PorterStemmer


MY_NAME = "Daniel Oliveros"
MY_EMAIL = "danioliable@hotmail.com"

MY_ANUM = 0000000  # put your A number here without 'A'

# the COLLABORATORS list contains tuples of 2 items, the name of the helper
# and their contribution to your homework
COLLABORATORS = [
    ('Garrett Bogart', 'Saved my life'),
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
    def index_dir(self, base_path):
        num_files_indexed = 0
        tokened_terms = []
        fileIndex = 0;
        for file in glob.glob(base_path + "*"):
            fileIndex+=1
            num_files_indexed += 1
            fileThing = open(file, "r",encoding="utf-8")
            gatheredString = fileThing.read()
            print("Tokenizing")
            splitStr = gatheredString.split()
            tokened_terms = self.tokenize(splitStr)
            stemmed = self.stemming(tokened_terms)
            for word in stemmed:
                if not self._inverted_index.__contains__(word):
                    #self._inverted_index[word] = fileIndex
                    self._inverted_index.setdefault(word, []).append(fileIndex)
                    #print(self._inverted_index[word])
                else:
                    found = False
                    for doc in self._inverted_index[word]:
                        if doc == fileIndex:
                            found = True
                    if found == False:
                        self._inverted_index[word].append(fileIndex)
                        # PUT YOUR CODE HERE
        #print(self._inverted_index)
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

    # purpose: convert a string of terms into a list of tokens.
    # convert a list of tokens to a list of stemmed tokens,
    # preconditions: tokenize a string of terms
    # returns: list of stemmed tokens
    # parameters:
    #   tokens - a list of tokens
    def stemming(self, tokens):
        stemmed_tokens = []
        porter = PorterStemmer.PorterStemmer()
        for word in tokens:
            stemmed_tokens.append(porter.stem(word,0,len(word)-1))
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
        actualResults = ""
        stem = PorterStemmer.PorterStemmer()
        words = text.split()
        if words.__len__() == 1:
            results = self._inverted_index[stem.stem(words[0],0, len(words[0])-1)]
        else:
            results1 = self._inverted_index[stem.stem(words[0],0, len(words[0])-1)]
            #print (results1)
            results2 = self._inverted_index[stem.stem(words[2], 0, len(words[2]) - 1)]
            #print(results2)

            if words[1]=="AND":
                results = (set(results1) & set(results2))
            if words[1] == "OR":
                results = set(results1).union(results2)
        # PUT YOUR CODE HERE

        for thingy in results:
            actualResults += str(thingy)
        #print (actualResults)
        return actualResults


# now, we'll define our main function which actually starts the indexer and
# does a few queries
def main(args):
    print(student)
    index = Index()
    print("starting indexer")
    num_files = index.index_dir('data/')
    print("indexed %d files" % num_files)
    for term in ('football', 'mike', 'sherman', 'mike OR sherman', 'mike AND sherman'):
        results = index.boolean_search(term)
        print("searching: %s -- results: %s" % (term, ", ".join(results)))


# this little helper will call main() if this file is executed from the command
# line but not call main() if this file is included as a module
if __name__ == "__main__":
    import sys

    main(sys.argv)
