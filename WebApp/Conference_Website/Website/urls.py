# Website/urls.py
from django.conf.urls import url
from Website import views

urlpatterns = [
    url(r'^$', views.HomePageView.as_view()),
]