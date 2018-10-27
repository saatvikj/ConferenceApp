from django.shortcuts import render
from django.views.generic import TemplateView
from django.http import HttpResponse
import csv
import os
import subprocess
import random, string
from Website.models import Conference
from Website.models import UserConference
from django.conf import settings
from .forms import ConferenceData
from django.core.files.storage import FileSystemStorage
from datetime import datetime
from django.contrib.auth import login, authenticate
from Website.forms import SignUpForm
from django.shortcuts import render, redirect
from django.contrib.auth.decorators import login_required
from django.core.mail import send_mail
import pycountry
import Website.firebase_wrapper as fw
import pyrebase

global_data = []
joining_code_list = []

config = {
  "apiKey": "AIzaSyCNpkzcclTXDCBdNlApdFOa7i7i1c2UPgM",
  "authDomain": "conference-portal-deb1c.firebaseapp.com",
  "databaseURL": "https://conference-portal-deb1c.firebaseio.com/",
  "storageBucket": "gs://conference-portal-deb1c.appspot.com"
}

def joining_code_generator():
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
			user_dictionary["Name"] = row[0]
			user_dictionary["Email"] = row[1]
			user_dictionary["Company"] = row[2]
			user_dictionary["Location"] = row[3]
			user_dictionary["joining_code"] = joining_code_generator()
			db.child(conference_id).child("Users").push(user_dictionary)

def signup(request):
	if request.method == 'POST':
		form = SignUpForm(request.POST)
		if form.is_valid():
			user = form.save()
			user.refresh_from_db()  # load the profile instance created by the signal
			user.profile.organization = form.cleaned_data.get('organization')
			country_name = form.cleaned_data.get('location').capitalize()
			user.profile.location = pycountry.countries.get(name=country_name).alpha_2
			user.save()
			raw_password = form.cleaned_data.get('password1')
			user = authenticate(username=user.username, password=raw_password)
			login(request, user)
			return redirect('overview')
	else:
		form = SignUpForm()
	return render(request, 'signup.html', {'form': form})


def login_user(request):
	username = request.POST['username']
	password = request.POST['password']
	user = authenticate(request, username=username, password=password)
	if user is not None:
		login(request, user)
		return redirect('dashboard')
	return render(request, 'index.html', {})    

def contact_email(request):
	if request.method == 'POST':
		name = request.POST['name']
		email = request.POST['email']
		message = request.POST['message']
		send_mail(
		    'Query from: '+ name,
		    message,
		    email,
		    ['saatvik16261@iiitd.ac.in', 'meghna16056@iiitd.ac.in'],
		    fail_silently=False,
		)
		return render(request, 'contact.html', {})

@login_required
def dashboard(request):
	user_conference_ids = UserConference.objects.filter(user_id=request.user.profile.profile_id)
	conference_id_list = []
	for conference in user_conference_ids:
		conference_id_list.append(conference.conference_id)
	user_conferences = Conference.objects.filter(conference_id__in=conference_id_list)
	return render(request, 'dashboard.html', {'conferences':user_conferences})

@login_required
def delete_conference(request):
	return 

class HomePageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index.html', {})

	def post(self, request, *args, **kwargs):
		username = request.POST['username']
		password = request.POST['password']
		user = authenticate(request, username=username, password=password)
		if user is not None:
			login(request, user)
			return redirect('dashboard')
		return render(request, 'index.html', {})


class ProfileView(TemplateView):
	def get(self, request, *args, **kwargs):
		path = request.get_full_path().split("/")[2]
		conference = Conference.objects.get(conference_id=path)
		return render(request, 'profile.html', {'conference': conference})

	def post(self, request, *args, **kwargs):
		username = request.POST['username']
		password = request.POST['password']
		user = authenticate(request, username=username, password=password)
		if user is not None:
			login(request, user)
			return redirect('dashboard')
		return render(request, 'index.html', {})		    	


class ThankYouPageView(TemplateView):
	def get(self, request, *args, **kwargs):
		apk_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/build/outputs/apk/debug/app-debug.apk')
		with open(apk_path, 'rb') as fh:
			response = HttpResponse(fh.read(), content_type="application/binary")
			response['Content-Disposition'] = 'inline; filename=app-debug.apk'
			return response
		return render(request, 'thank_you.html', {})

	def post(self, request, *args, **kwargs):
		apk_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/build/outputs/apk/debug/app-debug.apk')
		with open(apk_path, 'rb') as fh:
			response = HttpResponse(fh.read(), content_type="application/binary")
			response['Content-Disposition'] = 'inline; filename=app-debug.apk'
			return response
		return render(request, 'thank_you.html', {})


@login_required
def create_conference(request):
	return render(request, 'create.html', {})

@login_required
def create_conference_1(request):
	global global_data
	global_data = []
	data = request.POST

	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			global_data.append(value)
	return render(request, 'create2.html', {})

@login_required
def create_conference_2(request):
	data = request.POST
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			global_data.append(value)
	return render(request, 'create3.html', {})

@login_required
def create_conference_3(request):
	if len(global_data) == 9:
		global_data.append([])
	data = request.POST
	temp_list = []
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			temp_list.append(value)
	global_data[9].append(temp_list)				
	
	return render(request, 'create3.html', {})	

@login_required
def create_conference_4(request):
	if len(global_data) == 10:
		global_data.append([])
	data = request.POST
	temp_list = []
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			temp_list.append(value)
	global_data[10].append(temp_list)			
	
	return render(request, 'create4.html', {})

@login_required
def create_conference_5(request):
	if request.POST:
		conference_ob = Conference(
			conference_name=global_data[0],
			conference_venue=global_data[1],
			conference_start_date=global_data[2],
			conference_end_date=global_data[3],
			conference_description=global_data[4],
			conference_website=global_data[5],
			conference_facebook=global_data[6],
			conference_twitter=global_data[7],
			conference_email=global_data[8],
			conference_sponsors=global_data[9],
			conference_food_guide=global_data[10],
			conference_schedule_csv=request.FILES['schedule_csv'],
			conference_user_csv=request.FILES['users_csv'],
			conference_logo=request.FILES['logo_image'])

		conference_ob.save()
		user_csv_path = settings.MEDIA_ROOT+"/"+str(conference_ob.conference_id)+"/conference_user.csv"
		populate_db_with_users(user_csv_path, conference_ob.conference_id)
		user_conference_ob = UserConference(user_id=request.user.profile.profile_id, conference_id=conference_ob.conference_id)
		user_conference_ob.save()
		global_data.append(str(conference_ob.conference_id))	
		conference_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + global_data[11] + '/conference_data.csv')
		with open(conference_csv_path, 'w') as f:
			writer = csv.writer(f, delimiter=',')
			temp = iter(global_data)
			writer.writerow(temp)

		return render(request, 'thank_you.html', {})

	else:
		return render(request, 'create5.html', {})