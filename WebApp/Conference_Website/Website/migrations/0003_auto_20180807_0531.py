# Generated by Django 2.0.6 on 2018-08-07 05:31

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Website', '0002_auto_20180807_0416'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='profile',
            name='bio',
        ),
        migrations.RemoveField(
            model_name='profile',
            name='birth_date',
        ),
        migrations.RemoveField(
            model_name='profile',
            name='location',
        ),
    ]
