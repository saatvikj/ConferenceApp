import pyrebase
import csv

config = {
  "apiKey": "AIzaSyCNpkzcclTXDCBdNlApdFOa7i7i1c2UPgM",
  "authDomain": "conference-portal-deb1c.firebaseapp.com",
  "databaseURL": "https://conference-portal-deb1c.firebaseio.com/",
  "storageBucket": "gs://conference-portal-deb1c.appspot.com"
}

def populate_db_with_users(csv_name,conference_id):
	firebase = pyrebase.initialize_app(config)
	db = firebase.database()
	with open(csv_name,'r') as f:
		reader = csv.reader(f)
		next(f, None)

		for row in reader:
			user_dictionary = {}
			user_dictionary["Name"] = row[0]
			user_dictionary["Email"] = row[1]
			user_dictionary["Company"] = row[2]
			user_dictionary["Location"] = row[3]
			db.child(conference_id).child("Users").push(user_dictionary)
			