class ChangeLoginname < ActiveRecord::Migration
  def self.up
  remove_column :users,:loginName 
  add_column :users,:Name,:string 
  end

  def self.down
  add_column :users,:loginName 
  remove_column :users,:Name
  
  end
end
