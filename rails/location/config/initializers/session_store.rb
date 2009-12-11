# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_location_session',
  :secret      => '2a15345c13a2ac084a86eb4857c4d5753d98ac5d139c58e0d7633815a97a3fb5b38026fefb99f40db230d11dbd7f4bc906391f1cc947fa2cf9cd842168c33114'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
