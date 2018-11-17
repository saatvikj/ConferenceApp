from django.shortcuts import render
from django.views.generic import TemplateView
from django.http import HttpResponse
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from django.contrib.auth import login, authenticate
from django.shortcuts import render, redirect
from django.contrib.auth.decorators import login_required
from django.core.mail import send_mail
from .forms import ConferenceData
from Website.models import Conference
from Website.models import UserConference
from Website.forms import SignUpForm
import Website.utilities as utils
import pycountry
import pyrebase
import csv
import os
import subprocess
import random, string
from datetime import datetime
import shutil

global_data = []

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
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id_to_modify+'/logo.png'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/res/drawable-xxxhdpi/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id_to_modify+'/conference_data.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id_to_modify+'/conference_schedule.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
			shutil.copy(os.path.join(settings.MEDIA_ROOT,conference_id_to_modify+'/conference_user.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))

			subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/appnamechange.sh'),name])
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

		shutil.copy(os.path.join(settings.MEDIA_ROOT,global_data[11]+'/logo.png'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/res/drawable-xxxhdpi/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,global_data[11]+'/conference_data.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,global_data[11]+'/conference_schedule.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))
		shutil.copy(os.path.join(settings.MEDIA_ROOT,global_data[11]+'/conference_user.csv'), os.path.join(settings.FILES_DIR,'MobileApp/app/src/main/assets/'))

		subprocess.call([os.path.join(settings.FILES_DIR, 'MobileApp/appnamechange.sh'),global_data[0]])
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
		utils.populate_db_with_users(user_csv_path, conference_ob.conference_id)
		user_conference_ob = UserConference(user_id=request.user.profile.profile_id, conference_id=conference_ob.conference_id)
		user_conference_ob.save()
		global_data.append(str(conference_ob.conference_id))	
		conference_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + global_data[11] + '/conference_data.csv')
		with open(conference_csv_path, 'w') as f:
			writer = csv.writer(f, delimiter=',')
			temp = iter(global_data)
			writer.writerow(temp)

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
		username = request.POST['username']
		password = request.POST['password']
		user = authenticate(request, username=username, password=password)
		if user is not None:
			login(request, user)
			return redirect('dashboard')
		return render(request, 'index.html', {})