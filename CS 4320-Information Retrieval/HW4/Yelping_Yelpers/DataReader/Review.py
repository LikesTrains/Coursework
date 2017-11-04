class Review:
    def __init__(self, userid = None, businessid = None, stars = None, text=None, date=None, useful=None, funny=None, cool=None):
        if userid is None:
            self.userID = ""
        else:
            self.userID = userid
        if businessid is None:
            self.businessID = ""
        else:
            self.businessID = businessid
        if stars is None:
            self.stars = 0
        else:
            self.stars = stars
        if text is None:
            self.text = ""
        else:
            self.text = text
        if date is None:
            self.date = ""
        else:
            self.date = date
        if useful is None:
            self.useful = 0
        else:
            self.useful = useful
        if funny is None:
            self.funny = 0
        else:
            self.funny = funny
        if cool is None:
            self.cool = 0
        else:
            self.cool = cool