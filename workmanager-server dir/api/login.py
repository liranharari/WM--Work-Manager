from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User


class logInManager(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		password=self.request.get('password')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write('Wrong username')
			return
		if not user.checkPassword(password):
			self.error(403)
			self.response.write('Wrong password')
			return
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/loginManager',logInManager)
], debug=True)