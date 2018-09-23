from django.contrib import admin
from Website.models import Conference
from Website.models import Profile
from Website.models import UserConference
# Register your models here.
admin.site.register(Conference)
admin.site.register(Profile)
admin.site.register(UserConference)