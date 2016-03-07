from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User


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

class logInWorker(webapp2.RequestHandler):
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
	('/api/getuserinfo',getUserInfo)
], debug=True)