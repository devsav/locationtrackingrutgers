require 'test_helper'

class LoginControllerTest < ActionController::TestCase

def self.random_string(len)
   #generate a random password consisting of strings and digits
   chars = ("a".."z").to_a + ("A".."Z").to_a + ("0".."9").to_a
   newpass = ""
   1.upto(len) { |i| newpass << chars[rand(chars.size-1)] }
   return newpass
 end

def password=(pass)
  @password=pass
  self.salt = User.random_string(10) if !self.salt?
  self.hashed_password = User.encrypt(@password, self.salt)
end

 def self.encrypt(pass, salt)
   Digest::SHA1.hexdigest(pass+salt)
 end

def self.authenticate(login, pass)
  u=find(:first, :conditions=>["login = ?", login])
  return nil if u.nil?
  return u if User.encrypt(pass, u.salt)==u.hashed_password
  nil
end  

def send_new_password
  new_pass = User.random_string(10)
  self.password = self.password_confirmation = new_pass
  self.save
  Notifications.deliver_forgot_password(self.email, self.login, new_pass)
end


  # Replace this with your real tests.
  test "the truth" do
    assert true
  end
end
