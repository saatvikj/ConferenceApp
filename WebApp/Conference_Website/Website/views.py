from django.shortcuts import render
from django.views.generic import TemplateView
from django.http import HttpResponse
import csv
import os
import subprocess
from django.conf import settings
from .forms import ConferenceData
from django.core.files.storage import FileSystemStorage

global_data = []

# Create your views here.
class HomePageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index.html', {})

	def post(self, request, *args, **kwargs):
		global global_data
		global_data = []
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		return render(request, 'index2.html', {})    	

class Index2PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index2.html', {})

	def post(self, request, *args, **kwargs):
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		return render(request, 'index3.html', {})

class Index3PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index3.html', {})

	def post(self, request, *args, **kwargs):
		if len(global_data) == 9:
			global_data.append([])
		data = request.POST
		temp_list = []
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				temp_list.append(value)
		global_data[9].append(temp_list)				
		
		return render(request, 'index3.html', {})

class Index4PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index4.html', {})

	def post(self, request, *args, **kwargs):
		if len(global_data) == 10:
			global_data.append([])
		data = request.POST
		temp_list = []
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				temp_list.append(value)
		global_data[10].append(temp_list)				
		
		return render(request, 'index4.html', {})

class Index5PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		print(global_data)
		return render(request, 'index5.html', {})

	def post(self, request, *args, **kwargs):
		print(global_data)
		conference_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/conference_data.csv')
		with open(conference_csv_path, 'w') as f:
			writer = csv.writer(f, delimiter=',')
			temp = iter(global_data)
			writer.writerow(temp)
		paper_details_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/paper_details.csv')
		with open(paper_details_csv_path, 'wb+') as destination:
			for chunk in request.FILES["schedule_csv_file"].chunks():
				destination.write(chunk)

		user_details_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/user_details.csv')
		with open(user_details_csv_path, 'wb+') as destination:
			for chunk in request.FILES["users_csv_file"].chunks():
				destination.write(chunk)

		storage = FileSystemStorage()
		logo_image_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/res/drawable-xxxhdpi/logo.png')
		if os.path.isfile(logo_image_path):
			os.remove(logo_image_path) 
		content = request.FILES['logo_image_file']
		name = storage.save('logo.png', content)                          
		url = storage.url(name)  

		return render(request, 'thank_you.html', {})

class ThankYouPageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'thank_you.html', {})

	def post(self, request, *args, **kwargs):
		return render(request, 'thank_you.html', {})