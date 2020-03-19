# Website/urls.py
from Website import views
from django.conf.urls import url
from django.contrib.auth import views as auth_views
from django.urls import path, re_path
from django.views.generic import TemplateView

urlpatterns = [
    url(r'^$', views.HomePageView.as_view(), name=''),
    re_path(r'^profile/.*$', views.ProfileView.as_view()),
    url(r'^overview/', TemplateView.as_view(template_name='overview.html'),name='overview'),
    url(r'^guide/', TemplateView.as_view(template_name='guide.html'),name='guide'),
    url(r'^about/', TemplateView.as_view(template_name='about.html'),name='about'),
    url(r'^faq/', TemplateView.as_view(template_name='faq.html'),name='faq'),
    url(r'^contact/', TemplateView.as_view(template_name='contact.html'),name='contact'),
    url(r'^signup/$', views.signup, name='signup'),
    url(r'^$/login/$', views.login_user, name='login_user'),
    url(r'^dashboard/$', views.dashboard, name='dashboard'),
    url(r'^$', auth_views.logout, {'template_name': 'index.html'}, name='logout'),
    url(r'^create/$', views.create_conference),    
    url(r'^create1/$', views.create_conference_1),
    url(r'^create2/$', views.create_conference_2),
    url(r'^create3/$', views.create_conference_3),
    url(r'^create4/$', views.create_conference_4),
    url(r'^create5/$', views.create_conference_5),
    url(r'^thank_you/$',views.thank_you, name='thank_you'),
    url(r'^email/$', views.contact_email, name='email'),
]