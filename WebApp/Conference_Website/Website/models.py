from django.db import models
from django.template.defaultfilters import slugify
from django.contrib.auth.models import User
from django.conf import settings
import os
# Create your models here.

def get_upload_imagefilepath(instance, filename):
	name, ext = filename.split('.')
	print("Inside function")
	return "%s/%s.%s" %(instance.conference_id,"logo",ext)

def get_upload_schedulecsvfilepath(instance, filename):
	name, ext = filename.split('.')
	print("Inside function")
	return "%s/%s.%s" %(instance.conference_id,"conference_schedule",ext)

def get_upload_usercsvfilepath(instance, filename):
	name, ext = filename.split('.')
	print("Inside function")
	return "%s/%s.%s" %(instance.conference_id,"conference_user",ext)

class Conference(models.Model):

	conference_id = models.CharField(max_length=255, primary_key=True)
	conference_name = models.CharField(max_length=255)
	conference_venue = models.TextField()
	conference_start_date = models.DateField(auto_now=False,auto_now_add=False)
	conference_end_date = models.DateField(auto_now=False,auto_now_add=False)
	conference_description = models.TextField()
	conference_website = models.URLField(max_length=1023, null=True, blank=True)
	conference_facebook = models.CharField(max_length=255)
	conference_twitter = models.CharField(max_length=255)	
	conference_email = models.EmailField(max_length=255)
	conference_sponsors = models.TextField()
	conference_food_guide = models.TextField()
	conference_schedule_csv = models.FileField(upload_to=get_upload_schedulecsvfilepath, max_length=100)
	conference_user_csv = models.FileField(upload_to=get_upload_usercsvfilepath, max_length=100)	
	conference_logo = models.ImageField(upload_to=get_upload_imagefilepath, height_field=None, width_field=None, max_length=100)
	