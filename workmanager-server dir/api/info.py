from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.costumer import Costumer


class getUserInfo(webapp2.RequestHandler):
	def get(self):
  		template_params={}
		mail=self.request.get('mail')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write('Error')
			return
		
		template_params['code']=user.code
		template_params['managerPricing']=user.managerPricing
		template_params['workerPricing']=user.workerPricing
		self.response.write(json.dumps(template_params))

class getCostumerInfo(webapp2.RequestHandler):
	def get(self):
  		template_params={}
		mail=self.request.get('mail')
		name=self.request.get('name')
		
		user= User.query(User.mail == mail).get()
		
		costumer=Costumer.query(Costumer.name==name, Costumer.user==user.mail).get()
		if not costumer:
			self.error(403)
			self.response.write('no costumer')
			return
		
		template_params['address']=costumer.address
		template_params['phone']=costumer.phone
		template_params['email']=costumer.email
		template_params['field1']=costumer.field1
		template_params['field2']=costumer.field2
		template_params['field3']=costumer.field3
		template_params['field4']=costumer.field4
		template_params['field5']=costumer.field5
		self.response.write(json.dumps(template_params))

class getUserCostumers(webapp2.RequestHandler):
	def get(self):
		template_params={}
		code=self.request.get('code')
		
		user= User.query(User.code == code).get()
		if not user:
			self.error(403)
			self.response.write('Wrong code')
			return
		template_params['status']="OK"
		template_params['user']=user.mail
		template_params['type']="worker"
		self.response.write(json.dumps(template_params))

app = webapp2.WSGIApplication([
	('/api/getuserinfo',getUserInfo),
	('/api/getcostumerinfo',getCostumerInfo)
], debug=True)