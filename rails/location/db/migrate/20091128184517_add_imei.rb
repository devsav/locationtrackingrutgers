class AddImei < ActiveRecord::Migration
  def self.up
  	add_column :users, :imeiNumber, :string
  end

  def self.down
	remove_column :users, :imeiNumber
  end
end
