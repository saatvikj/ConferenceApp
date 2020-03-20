import Website.utilities as utils
import csv
import os
import pandas as pd
import pycountry
import pyrebase
import random
import shutil
import string
import subprocess
from Website.forms import SignUpForm
from Website.models import Conference
from Website.models import UserConference
from datetime import datetime
from django.conf import settings
from django.contrib.auth import login, authenticate
from django.contrib.auth.decorators import login_required
from django.core.files.storage import FileSystemStorage
from django.core.mail import send_mail
from django.http import HttpResponse
from django.shortcuts import render
from django.shortcuts import render, redirect
from django.views.generic import TemplateView

from .forms import ConferenceData

basic_conference_details = pd.DataFrame(columns=['id','conference-name','conference-venue','start-date','end-date','about','website','facebook','twitter','contact'])
conference_sponsors = pd.DataFrame(columns=['company','type','website'])
conference_food_events = pd.DataFrame(columns=['event-name','location','details','start-time','end-time'])

def signup(request):
	if request.method == 'POST':
		form = SignUpForm(request.POST)
		if form.is_valid():
			user = form.save()
			user.refresh_from_db()
			user.profile.organization = form.cleaned_data.get('organization')
			country_name = form.cleaned_data.get('location').capitalize()
			user.profile.location = pycountry.countries.get(name=country_name).alpha_2
			user.save()
			raw_password = form.cleaned_data.get('password1')
			user = authenticate(username=user.username, password=raw_password)
			login(request, user)
			return redirect('overview')
		else:
			return render(request, 'signup.html', {'form': form})
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
	if request.method == 'GET':
		user_conference_ids = UserConference.objects.filter(user_id=request.user.profile.profile_id)
		conference_id_list = []
		for conference in user_conference_ids:
			conference_id_list.append(conference.conference_id)
		user_conferences = Conference.objects.filter(conference_id__in=conference_id_list)
		return render(request, 'dashboard.html', {'conferences':user_conferences})
	else:
		conference_id_to_modify = request.POST['id']
		
		if request.POST['function'] == 'delete':
			Conference.objects.get(conference_id=conference_id_to_modify).delete()
			UserConference.objects.get(conference_id=conference_id_to_modify).delete()
			shutil.rmtree(os.path.join(settings.MEDIA_ROOT,conference_id_to_modify+'/'))
			utils.delete_conference_from_db(conference_id_to_modify)

		else:
			conference = Conference.objects.get(conference_id=conference_id_to_modify)
			name = conference.conference_name
			conference_id = str(conference.conference_id)
			pColor = conference.conference_primarycolor
			sColor = conference.conference_secondarycolor
			aColor = conference.conference_accentcolor

			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/logo.png'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/res/drawable-xxxhdpi/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_data.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_schedule.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_user.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_sponsors.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_food_events.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))

			subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/appnamechange.sh'),name])
			subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/colorchange.sh'), pColor, sColor, aColor])
			subprocess.call(os.path.join(settings.FILES_DIR, 'MobileApp/generator.sh'))

			apk_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/build/outputs/apk/debug/app-debug.apk')
			with open(apk_path, 'rb') as fh:
				response = HttpResponse(fh.read(), content_type="application/binary")
				response['Content-Disposition'] = 'inline; filename=app-debug.apk'
				return response

		user_conference_ids = UserConference.objects.filter(user_id=request.user.profile.profile_id)
		conference_id_list = []
		for conference in user_conference_ids:
			conference_id_list.append(conference.conference_id)
		user_conferences = Conference.objects.filter(conference_id__in=conference_id_list)

		return render(request, 'dashboard.html', {'conferences':user_conferences})		

@login_required
def thank_you(request):
	if request.method == 'POST':

		allotted_id = basic_conference_details.loc[0,'id']
		conference = Conference.objects.get(conference_id=allotted_id)

		pColor = conference.conference_primarycolor
		sColor = conference.conference_secondarycolor
		aColor = conference.conference_accentcolor

		shutil.copy(os.path.join(settings.MEDIA_ROOT,allotted_id+'/logo.png'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/res/drawable-xxxhdpi/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,allotted_id+'/conference_data.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,allotted_id+'/conference_schedule.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,allotted_id+'/conference_user.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_sponsors.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id+'/conference_food_events.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))

		subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/appnamechange.sh'),basic_conference_details.loc[0,'conference-name']])
		subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/colorchange.sh'), pColor, sColor, aColor])
		subprocess.call(os.path.join(settings.FILES_DIR, 'MobileApp/generator.sh'))

		apk_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/build/outputs/apk/debug/app-debug.apk')
		with open(apk_path, 'rb') as fh:
			response = HttpResponse(fh.read(), content_type="application/binary")
			response['Content-Disposition'] = 'inline; filename=app-debug.apk'
			return response
		return render(request, 'dashboard.html', {})

	else:
		return render(request, 'thank_you.html', {})

@login_required
def create_conference(request):
	return render(request, 'create.html', {})

@login_required
def create_conference_1(request):
	data = request.POST

	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			basic_conference_details.loc[0,key] = value
	return render(request, 'create2.html', {})

@login_required
def create_conference_2(request):
	data = request.POST
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			basic_conference_details.loc[0,key] = value			
	return render(request, 'create3.html', {})

@login_required
def create_conference_3(request):
	data = request.POST
	number_of_items = conference_sponsors.shape[0]
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			conference_sponsors.loc[number_of_items, key] = value

	return render(request, 'create3.html', {})	

@login_required
def create_conference_4(request):
	data = request.POST
	number_of_items = conference_food_events.shape[0]
	for key, value in data.items():
		if key != "csrfmiddlewaretoken":
			conference_food_events.loc[number_of_items, key] = value
	
	return render(request, 'create4.html', {})

@login_required
def create_conference_5(request):
	if request.POST:

		pColor = ""
		sColor = ""
		aColor = ""

		data = request.POST
		for key, value in data.items():
			if key == "primarycolor":
				pColor = value
			elif key == "secondarycolor":
				sColor = value
			elif key == "accentcolor":
				aColor = value

		conference_ob = Conference(
			conference_name=basic_conference_details.loc[0,'conference-name'],
			conference_venue=basic_conference_details.loc[0,'conference-venue'],
			conference_start_date=basic_conference_details.loc[0,'start-date'],
			conference_end_date=basic_conference_details.loc[0,'end-date'],
			conference_description=basic_conference_details.loc[0,'about'],
			conference_website=basic_conference_details.loc[0,'website'],
			conference_facebook=basic_conference_details.loc[0,'facebook'],
			conference_twitter=basic_conference_details.loc[0,'twitter'],
			conference_email=basic_conference_details.loc[0,'contact'],
			conference_schedule_csv=request.FILES['schedule_csv'],
			conference_user_csv=request.FILES['users_csv'],
			conference_logo=request.FILES['logo_image'],
			conference_primarycolor=pColor,
			conference_secondarycolor=sColor,
			conference_accentcolor=aColor)

		conference_ob.save()
		
		user_csv_path = settings.MEDIA_ROOT+"/"+str(conference_ob.conference_id)+"/conference_user.csv"
		utils.populate_db_with_users(user_csv_path, conference_ob.conference_id)
		user_conference_ob = UserConference(user_id=request.user.profile.profile_id, conference_id=conference_ob.conference_id)
		user_conference_ob.save()

		basic_conference_details.loc[0,'id'] = str(conference_ob.conference_id)
		
		conference_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + basic_conference_details.loc[0,'id'] + '/conference_data.csv')
		basic_conference_details.to_csv(conference_csv_path)

		conference_food_events_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + basic_conference_details.loc[0,'id'] + '/conference_food_events.csv')
		conference_food_events.to_csv(conference_food_events_csv_path)

		conference_sponsors_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + basic_conference_details.loc[0,'id'] + '/conference_sponsors.csv')
		conference_sponsors.to_csv(conference_sponsors_csv_path)


		return redirect('thank_you')
	else:
		return render(request, 'create5.html', {})

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
		data = request.POST
		conference_id = data['conference_id']
		conference_ob = Conference.objects.get(conference_name=conference_id)
		conference_ob.conference_venue = data['conference_venue']
		conference_ob.conference_start_date = data['conference_start_date']
		conference_ob.conference_end_date = data['conference_end_date']
		conference_ob.conference_description = data['conference_description']
		conference_ob.conference_website = data['conference_website']
		conference_ob.conference_facebook = data['conference_facebook']
		conference_ob.conference_twitter = data['conference_twitter']
		conference_ob.conference_email = data['conference_email']
		conference_ob.conference_primarycolor = data.get('primarycolor')
		conference_ob.conference_secondarycolor = data.get('secondarycolor')
		conference_ob.conference_accentcolor = data.get('accentcolor')
		conference_ob.save()

		return redirect('dashboard')
