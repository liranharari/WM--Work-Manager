from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.customer import Customer


class updateCustomer(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		name=self.request.get('name')
		newname=self.request.get('newname')
		address=self.request.get('address')
		phone=self.request.get('phone')
		email=self.request.get('email')
		field1=self.request.get('field1')
		field2=self.request.get('field2')
		field3=self.request.get('field3')
		field4=self.request.get('field4')
		field5=self.request.get('field5')
		field6=self.request.get('field6')
		
		user = User.query(User.mail == mail).get()
			
		Customer.updateCustomer(mail, name, newname, address, phone, email, field1, field2, field3, field4, field5, field6)
		
		
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/updatecustomer',updateCustomer)
], debug=True)