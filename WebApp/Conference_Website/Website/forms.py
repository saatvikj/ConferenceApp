from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User

class ConferenceData(forms.Form):
	name = forms.CharField(label='Name')
	venue = forms.CharField(label='Venue')
	about = forms.CharField(label='About')
	venue_details = forms.CharField(label='In-Venue Locations')
	start_time = forms.CharField(label='Start Time')
	end_time = forms.CharField(label='End Time')
	partners = forms.CharField(label='Partners')

class SignUpForm(UserCreationForm):
	first_name = forms.CharField(max_length=30, required=True, help_text="First name")
	last_name = forms.CharField(max_length=30, required=True, help_text="Last Name")
	email = forms.EmailField(max_length=254, required=True, help_text="Email ID")
	organization = forms.CharField(max_length=1023, required=True, help_text="Organization")
	location = forms.CharField(max_length=255, required=True, label="Country")
	class Meta:
		model = User
		fields = ( 'first_name', 'last_name', 'username', 'email', 'password1', 'password2', 'organization', 'location')