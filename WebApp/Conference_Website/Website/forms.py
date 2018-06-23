from django import forms

class ConferenceData(forms.Form):
	name = forms.CharField(label='Name')
	venue = forms.CharField(label='Venue')
	about = forms.CharField(label='About')
	venue_details = forms.CharField(label='In-Venue Locations')
	start_time = forms.CharField(label='Start Time')
	end_time = forms.CharField(label='End Time')
	partners = forms.CharField(label='Partners')


