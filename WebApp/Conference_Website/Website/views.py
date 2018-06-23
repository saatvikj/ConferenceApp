from django.shortcuts import render
from django.views.generic import TemplateView
import csv
import os
from django.conf import settings
from .forms import ConferenceData

# Create your views here.
class HomePageView(TemplateView):
	def get(self, request, *args, **kwargs):
		form = ConferenceData()
		context ={"form": form}
		return render(request, 'index.html', context)

	def post(self, request, *args, **kwargs):
		form = ConferenceData(request.POST)
		context ={"form": form}
		if form.is_valid():
			print(form.cleaned_data)
			data = form.cleaned_data
			conference_data = []
			for key,value in data.items():
				conference_data.append(value)

			print(conference_data)
			file_path = os.path.join(settings.FILES_DIR, 'MobileApp/app/src/main/assets/conference_data.csv')
			with open(file_path, 'w') as f:
				writer = csv.writer(f, delimiter=',')
				temp = iter(conference_data)
				writer.writerow(temp)


		return render(request, 'index.html', context)    	
