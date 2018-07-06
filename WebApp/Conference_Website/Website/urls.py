# Website/urls.py
from django.conf.urls import url
from Website import views

urlpatterns = [
    url(r'^$', views.HomePageView.as_view()),
    url(r'^index2/', views.Index2PageView.as_view()),
    url(r'^index3/', views.Index3PageView.as_view()),
    url(r'^index4/', views.Index4PageView.as_view()),
    url(r'^index5/', views.Index5PageView.as_view()),
    url(r'^thank_you', views.ThankYouPageView.as_view()),
]