from django.shortcuts import render
from django.views.generic import TemplateView
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
		# form = ConferenceData(request.POST)
		# context ={"form": form}
		# if form.is_valid():
		# 	print(form.cleaned_data)
			# data = form.cleaned_data
			# conference_data = []
			# for key,value in data.items():
			# 	conference_data.append(value)

			# print(conference_data)
			# file_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/conference_data.csv')
			# with open(file_path, 'w') as f:
			# 	writer = csv.writer(f, delimiter=',')
			# 	temp = iter(conference_data)
			# 	writer.writerow(temp)

			# apk_generator_path = os.path.join(settings.FILES_DIR, '/MobileApp/generator.sh')
			# os.system('sudo' + apk_generator_path)
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
		data = request.POST
		for key, value in data.items():
			if key != "csrfmiddlewaretoken":
				global_data.append(value)
		print(global_data)
		file_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/conference_data.csv')
		with open(file_path, 'w') as f:
			writer = csv.writer(f, delimiter=',')
			temp = iter(global_data)
			writer.writerow(temp)
		return render(request, 'index3.html', {})