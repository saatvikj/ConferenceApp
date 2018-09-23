from django.db import models
from django.template.defaultfilters import slugify
from django.contrib.auth.models import User
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
import os
import uuid
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
	conference_id = models.UUIDField(primary_key=True, default=uuid.uuid4)
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
	
class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    organization = models.CharField(max_length=256, blank=True)
    profile_id = models.UUIDField(primary_key=True, default=uuid.uuid4)

    def __str__(self):
    	return self.user.first_name

@receiver(post_save, sender=User)
def update_user_profile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)
    instance.profile.save()

class UserConference(models.Model):
	user_id = models.TextField(max_length=255)
	conference_id = models.TextField(max_length=255)
