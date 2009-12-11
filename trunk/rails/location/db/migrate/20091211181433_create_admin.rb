class CreateAdmin < ActiveRecord::Migration
  def self.up
  add_column :users ,:admin,:boolean,:default=> 0
  user=User.create(:userName=>"admin",:password=>"admin",:admin=> 1,:contactNumber=>7323254855,:address=>"HighLand Park",:emailId=>"savio85@gmail.com",:name=>"Administrator")
user.save!	
  end

  def self.down
remove_column :users,:admin

  end
end
