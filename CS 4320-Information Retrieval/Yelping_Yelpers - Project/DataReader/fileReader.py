class Business_Maker:

    def __init__(self):
        self.business_dictionary = {}

    def readFile(self,path):
        fin = open(path, "r", encoding="utf8")
        formated = []
        for line in fin:
            temp = ""
            for letter in line:
                if letter.isalnum():
                    temp += letter
                else:
                    if letter == ",":
                        temp += ' '
                    if letter == "_":
                        temp += '_'
                    if letter == "-":
                        temp += '-'
                    if letter == ":":
                        temp += ':'
            formated.append(temp)
        #print(formated)
        return formated

    def formating_one(self,final):
        temp = []
        for word in final:
            temp.append(word[1])
        return temp

    def finalData(self,formated):
        final = []
        count = 0
        for business in formated:
            compress = business.split()
            holder = []
            for word in compress:
                temp = word.split(':')
                for w in temp:
                    if w == 'business_id':
                        holder.append(temp)
                    if w == 'state':
                        holder.append(temp)
                    if w == 'name':
                        holder.append(temp)
            final.append(self.formating_one(holder))
        return final

    def make_dictionary(self,business_list):
        business_index = {}
        for business in business_list:
            business_index[business[0]] = business[1]
        return business_index

    def make(self, path):
        formated = self.readFile(path)
        business_list = self.finalData(formated)
        self.business_dictionary = self.make_dictionary(business_list)
        #print("business class")
        #print(self.business_dictionary)
        return(self.business_dictionary)

class Review_Maker:
    def __init__(self):
        self.review_dictionary = {}

    def readFile(self, path):
        fin = open(path, "r", encoding="utf8")
        formated = []
        for line in fin:
            temp = ""
            for letter in line:
                if letter.isalnum():
                    temp += letter
                else:
                    if letter == ",":
                        temp += ' '
                    if letter == "_":
                        temp += '_'
                    if letter == "-":
                        temp += '-'
                    if letter == ":":
                        temp += ':'
                    if letter == " ":
                        temp+="_"
            formated.append(temp)
        #print(formated)
        return formated

    def formater(self, final):
        temp = []
        for word in final:
            temp.append(word[1])
        return temp

    def data(self, formating):
        final = []
        count = 0
        for business in formating:
            compress = business.split()
            holder = []
            for word in compress:
                temp = word.split(':')
                for w in temp:
                    if w == 'user_id':
                        holder.append(temp)
                    if w == 'business_id':
                        holder.append(temp)
                    if w == 'stars':
                        holder.append(temp)
                    if w == 'text':
                        holder.append(temp)
            final.append(self.formater(holder))
        return final


    def make_dictionary(self, review_list):
        for review in review_list:
            self.review_dictionary[review[0]] = review[1:4]
        return

    def make(self,path):
        temp = self.readFile(path)
        temp2 = self.data(temp)
        self.make_dictionary(temp2)
        #print(self.review_dictionary)
        return self.review_dictionary

