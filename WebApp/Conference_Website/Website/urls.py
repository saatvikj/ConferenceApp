# Website/urls.py
from django.conf.urls import url
from django.urls import path
from Website import views
from django.views.generic import TemplateView

urlpatterns = [
    url(r'^$', views.HomePageView.as_view()),
    url(r'^overview/', TemplateView.as_view(template_name='overview.html'),name='overview'),
    url(r'^guide/', TemplateView.as_view(template_name='guide.html'),name='guide'),
    url(r'^about/', TemplateView.as_view(template_name='about.html'),name='about'),
    url(r'^faq/', TemplateView.as_view(template_name='faq.html'),name='faq'),
    url(r'^contact/', TemplateView.as_view(template_name='contact.html'),name='contact'),
]