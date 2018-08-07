# Generated by Django 2.0.6 on 2018-08-06 10:04

import Website.models
from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion
import uuid


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Conference',
            fields=[
                ('conference_id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('conference_name', models.CharField(max_length=255)),
                ('conference_venue', models.TextField()),
                ('conference_start_date', models.DateField()),
                ('conference_end_date', models.DateField()),
                ('conference_description', models.TextField()),
                ('conference_website', models.URLField(blank=True, max_length=1023, null=True)),
                ('conference_facebook', models.CharField(max_length=255)),
                ('conference_twitter', models.CharField(max_length=255)),
                ('conference_email', models.EmailField(max_length=255)),
                ('conference_sponsors', models.TextField()),
                ('conference_food_guide', models.TextField()),
                ('conference_schedule_csv', models.FileField(upload_to=Website.models.get_upload_schedulecsvfilepath)),
                ('conference_user_csv', models.FileField(upload_to=Website.models.get_upload_usercsvfilepath)),
                ('conference_logo', models.ImageField(upload_to=Website.models.get_upload_imagefilepath)),
            ],
        ),
        migrations.CreateModel(
            name='Profile',
            fields=[
                ('bio', models.TextField(blank=True, max_length=500)),
                ('location', models.CharField(blank=True, max_length=30)),
                ('birth_date', models.DateField(blank=True, null=True)),
                ('organization', models.CharField(blank=True, max_length=256)),
                ('profile_id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
