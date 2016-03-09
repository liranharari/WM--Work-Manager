from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.field import Field


class signUp(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		password=self.request.get('password')
		code=self.request.get('code')
		managerPricing=self.request.get('managerPricing')
		workerPricing=self.request.get('workerPricing')
		field1=self.request.get('field1')
		field2=self.request.get('field2')
		field3=self.request.get('field3')
		field4=self.request.get('field4')
		field5=self.request.get('field5')
		field6=self.request.get('field6')
		
		user = User.query(User.mail == mail).get()
		if user:
			self.error(402)
			self.response.write('Email taken')
			return
		
		user=User()
		user.mail=mail
		user.setPassword(password)
		user.code=code
		user.managerPricing=int(managerPricing)
		user.workerPricing=int(workerPricing)
		user.put()
		Field.addfieldstouser(mail, field1, field2, field3, field4, field5, field6)
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/signup',signUp)
], debug=True)