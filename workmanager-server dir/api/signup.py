from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User


class signUp(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		password=self.request.get('password')
		code=self.request.get('code')
		managerPricing=self.request.get('managerPricing')
		workerPricing=self.request.get('workerPricing')
		
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
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/signup',signUp)
], debug=True)