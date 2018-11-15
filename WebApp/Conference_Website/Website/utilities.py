import pyrebase
import sys
import os
import random
import string
import csv

joining_code_list = []
config = {
  "apiKey": "AIzaSyCNpkzcclTXDCBdNlApdFOa7i7i1c2UPgM",
  "authDomain": "conference-portal-deb1c.firebaseapp.com",
  "databaseURL": "https://conference-portal-deb1c.firebaseio.com/",
  "storageBucket": "gs://conference-portal-deb1c.appspot.com"
}


def joining_code_generator():
	global joining_code_list
	x = ''.join(random.choices(string.ascii_letters + string.digits, k=4))
	while x in joining_code_list:
		x = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(4))
	joining_code_list.append(x)
	return x

def populate_db_with_users(csv_name,conference_id):
	firebase = pyrebase.initialize_app(config)
	db = firebase.database()
	with open(csv_name,'r') as f:
		reader = csv.reader(f)
		next(f, None)
		for row in reader:
			user_dictionary = {}
			user_dictionary["name"] = row[0]
			user_dictionary["email"] = row[1]
			user_dictionary["company"] = row[2]
			user_dictionary["location"] = row[3]
			user_dictionary["bio"] = row[4]
			user_dictionary["interests"] = row[5]
			user_dictionary["presentedPapers"] = row[6]
			user_dictionary["typeOfUser"] = row[7]
			user_dictionary["joining_code"] = joining_code_generator()
			db.child(conference_id).child("Users").push(user_dictionary)
