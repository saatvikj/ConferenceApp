# Website/urls.py
from django.conf.urls import url
from django.urls import path
from Website import views
from django.views.generic import TemplateView
from django.contrib.auth import views as auth_views

urlpatterns = [
    url(r'^$', views.HomePageView.as_view(), name=''),
    url(r'^overview/', TemplateView.as_view(template_name='overview.html'),name='overview'),
    url(r'^guide/', TemplateView.as_view(template_name='guide.html'),name='guide'),
    url(r'^about/', TemplateView.as_view(template_name='about.html'),name='about'),
    url(r'^faq/', TemplateView.as_view(template_name='faq.html'),name='faq'),
    url(r'^contact/', TemplateView.as_view(template_name='contact.html'),name='contact'),
    url(r'^signup/$', views.signup, name='signup'),
    url(r'^dashboard/$', views.dashboard, name='dashboard'),
    url(r'^logout/$', auth_views.logout, {'template_name': 'index.html'}, name='logout'),
]