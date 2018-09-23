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

global_data = []
conference_keys_list = []

def signup(request):
	if request.method == 'POST':
		form = SignUpForm(request.POST)
		if form.is_valid():
			user = form.save()
			user.refresh_from_db()  # load the profile instance created by the signal
			user.profile.organization = form.cleaned_data.get('organization')
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
		print("Hello")
		login(request, user)
		return redirect('dashboard')
	else:
		print("Bye")
	return render(request, 'index.html', {})    



@login_required
def dashboard(request):
	user_conference_ids = UserConference.objects.filter(user_id=request.user.profile.profile_id)
	conference_id_list = []
	for conference in user_conference_ids:
		conference_id_list.append(conference.conference_id)
	user_conferences = Conference.objects.filter(conference_id__in=conference_id_list)
	return render(request, 'dashboard.html', {'conferences':user_conferences})

# Create your views here.
class HomePageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index.html', {})

	def post(self, request, *args, **kwargs):
		username = request.POST['username']
		password = request.POST['password']
		user = authenticate(request, username=username, password=password)
		if user is not None:
			print("Hello")
			login(request, user)
			return redirect('dashboard')
		else:
			print("Bye")
		return render(request, 'index.html', {})    	

# class Index2PageView(TemplateView):
# 	def get(self, request, *args, **kwargs):
# 		return render(request, 'index2.html', {})

# 	def post(self, request, *args, **kwargs):
# 		data = request.POST
# 		for key, value in data.items():
# 			if key != "csrfmiddlewaretoken":
# 				global_data.append(value)
# 		return render(request, 'index3.html', {})

# class Index3PageView(TemplateView):
# 	def get(self, request, *args, **kwargs):
# 		return render(request, 'index3.html', {})

# 	def post(self, request, *args, **kwargs):
# 		if len(global_data) == 9:
# 			global_data.append([])
# 		data = request.POST
# 		temp_list = []
# 		for key, value in data.items():
# 			if key != "csrfmiddlewaretoken":
# 				temp_list.append(value)
# 		global_data[9].append(temp_list)				
		
# 		return render(request, 'index3.html', {})

# class Index4PageView(TemplateView):
# 	def get(self, request, *args, **kwargs):
# 		return render(request, 'index4.html', {})

# 	def post(self, request, *args, **kwargs):
# 		if len(global_data) == 10:
# 			global_data.append([])
# 		data = request.POST
# 		temp_list = []
# 		for key, value in data.items():
# 			if key != "csrfmiddlewaretoken":
# 				temp_list.append(value)
# 		global_data[10].append(temp_list)			
		
# 		return render(request, 'index4.html', {})

# class Index5PageView(TemplateView):
# 	def get(self, request, *args, **kwargs):
# 		print(global_data)
# 		return render(request, 'index5.html', {})

# 	def post(self, request, *args, **kwargs):
# 		print(global_data)
# 		global_data.append(conference_key)	

		# paper_details_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/paper_details.csv')
		# with open(paper_details_csv_path, 'wb+') as destination:
		# 	for chunk in request.FILES["schedule_csv_file"].chunks():
		# 		destination.write(chunk)

		# user_details_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/user_details.csv')
		# with open(user_details_csv_path, 'wb+') as destination:
		# 	for chunk in request.FILES["users_csv_file"].chunks():
		# 		destination.write(chunk)

		# storage = FileSystemStorage()
		# logo_image_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/res/drawable-xxxhdpi/logo.png')
		# if os.path.isfile(logo_image_path):
		# 	os.remove(logo_image_path) 
		# content = request.FILES['logo_image_file']
		# name = storage.save('logo.png', content)                          
		# url = storage.url(name)  
		# start_datetime = datetime.strptime(global_data[2],'%Y-%m-%d')
		# end_datetime = datetime.strptime(global_data[3],'%Y-%m-%d')		
		# conference_ob = Conference(conference_id=global_data[11],
		# 	conference_name=global_data[0],
		# 	conference_venue=global_data[1],
		# 	conference_start_date=global_data[2],
		# 	conference_end_date=global_data[3],
		# 	conference_description=global_data[4],
		# 	conference_website=global_data[5],
		# 	conference_facebook=global_data[6],
		# 	conference_twitter=global_data[7],
		# 	conference_email=global_data[8],
		# 	conference_sponsors=global_data[9],
		# 	conference_food_guide=global_data[10],
		# 	conference_schedule_csv=request.FILES['schedule_csv_file'],
		# 	conference_user_csv=request.FILES['users_csv_file'],
		# 	conference_logo=request.FILES['logo_image_file'])

		# conference_ob.save()
		
		# conference_csv_path = os.path.join(settings.FILES_DIR, 'WebApp/Conference_Website/media/' + global_data[11] + '/conference_data.csv')
		# with open(conference_csv_path, 'w') as f:
		# 	writer = csv.writer(f, delimiter=',')
		# 	temp = iter(global_data)
		# 	writer.writerow(temp)

		# return render(request, 'thank_you.html', {})

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