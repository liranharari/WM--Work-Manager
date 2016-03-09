#this model keeps the link
from google.appengine.ext import ndb
from user import User
class Field(ndb.Model):
	user = ndb.StringProperty() 
	field1 = ndb.StringProperty()
	field2 = ndb.StringProperty()
	field3 = ndb.StringProperty()
	field4 = ndb.StringProperty()
	field5 = ndb.StringProperty()
	field6 = ndb.StringProperty()

		
	@staticmethod
	def getAllFieldsPerUser(user):
		q = Field.query(Field.user == user)
		fields = []
		for field in q:
			fields.append({"field1": field.field1,
			"field2": field.field2,
			"field3": field.field3,
			"field4": field.field4,
			"field5": field.field5,
			"field6": field.field6})
		return fields

	@staticmethod
	def addfieldstouser(user, field1, field2, field3, field4, field5, field6):
		field=Field()
		field.user=user
		field.field1=field1
		field.field2=field2
		field.field3=field3
		field.field4=field4
		field.field5=field5
		field.field6=field6
		field.put()
		return