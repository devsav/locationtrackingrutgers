class ChangeLoginname < ActiveRecord::Migration
  def self.up
  remove_column :users,:loginName 
  add_column :users,:name,:string 
  end

  def self.down
  add_column :users,:loginName 
    remove_column :users,:name
  
  end
end
