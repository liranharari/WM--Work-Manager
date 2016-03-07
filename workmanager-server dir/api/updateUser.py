from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.customer import Customer


class updateUsermanagerPricing(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		managerPricing=self.request.get('managerpricing')
		
		user = User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write('No user - access denied')
			return
		User.updateManagerPricing(mail, int(managerPricing))
		
		self.response.write(json.dumps({"status": "OK"}))

class updateUserworkerPricing(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		workerPricing=self.request.get('workerpricing')
		
		user = User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write('No user - access denied')
			return
		User.updateWorkerPricing(mail, int(workerPricing))
		
		self.response.write(json.dumps({"status": "OK"}))

		
app = webapp2.WSGIApplication([
	('/api/updateusermanagerpricing',updateUsermanagerPricing),
	('/api/updateuserworkerpricing',updateUserworkerPricing)
], debug=True)