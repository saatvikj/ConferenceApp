# Generated by Django 2.0.6 on 2018-09-25 06:38

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Website', '0005_profile_location'),
    ]

    operations = [
        migrations.AlterField(
            model_name='profile',
            name='location',
            field=models.CharField(max_length=255),
        ),
        migrations.AlterField(
            model_name='profile',
            name='organization',
            field=models.CharField(blank=True, max_length=255),
        ),
    ]
