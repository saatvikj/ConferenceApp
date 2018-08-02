# Website/urls.py
from django.conf.urls import url
from django.urls import path
from Website import views
from django.views.generic import TemplateView

urlpatterns = [
    url(r'^$', views.HomePageView.as_view()),
    url(r'^overview/', TemplateView.as_view(template_name='overview.html'),name='overview'),
    url(r'^index3/', views.Index3PageView.as_view()),
    url(r'^index4/', views.Index4PageView.as_view()),
    url(r'^index5/', views.Index5PageView.as_view()),
    url(r'^thank_you', views.ThankYouPageView.as_view()),
]