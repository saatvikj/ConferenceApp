# Generated by Django 2.0.6 on 2019-05-10 10:16

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Website', '0006_auto_20180925_0638'),
    ]

    operations = [
        migrations.AddField(
            model_name='conference',
            name='conference_accentcolor',
            field=models.CharField(default='#58af0', max_length=255),
        ),
        migrations.AddField(
            model_name='conference',
            name='conference_primarycolor',
            field=models.CharField(default='#0277bd', max_length=255),
        ),
        migrations.AddField(
            model_name='conference',
            name='conference_secondarycolor',
            field=models.CharField(default='#004c8c', max_length=255),
        ),
    ]
