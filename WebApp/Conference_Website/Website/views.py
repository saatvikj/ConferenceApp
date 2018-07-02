from django.shortcuts import render
from django.views.generic import TemplateView
from django.http import HttpResponse
import csv
import os
import subprocess
from django.conf import settings
from .forms import ConferenceData

global_data = []

# Create your views here.
class HomePageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index.html', {})

	def post(self, request, *args, **kwargs):
		global global_data
		global_data = []
		print(request.POST)	
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		return render(request, 'index2.html', {})    	

class Index2PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index2.html', {})

	def post(self, request, *args, **kwargs):
		print(request.POST)
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		return render(request, 'index3.html', {})

class Index3PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index3.html', {})

	def post(self, request, *args, **kwargs):
		print(request.POST)
		if len(global_data) == 9:
			global_data.append([])
		data = request.POST
		temp_list = []
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				temp_list.append(value)
		print(len(global_data))
		global_data[9].append(temp_list)				
		print(global_data)
		
		return render(request, 'index3.html', {})

class Index4PageView(TemplateView):
	def get(self, request, *args, **kwargs):
		return render(request, 'index4.html', {})

	def post(self, request, *args, **kwargs):
		print(request.POST)
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		print(global_data)
		conference_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/conference_data.csv')
		with open(conference_csv_path, 'w') as f:
			writer = csv.writer(f, delimiter=',')
			temp = iter(global_data)
			writer.writerow(temp)
		paper_details_csv_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/paper_details.csv')
		with open(paper_details_csv_path, 'wb+') as destination:
			for chunk in request.FILES["csv_file"].chunks():
				destination.write(chunk)
		return render(request, 'index4.html',{})

