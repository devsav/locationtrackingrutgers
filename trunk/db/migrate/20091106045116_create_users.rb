class CreateUsers < ActiveRecord::Migration
  def self.up
    create_table :users do |t|
      t.string :loginName
      t.string :hashed_password
      t.string :salt
      t.string :userName
      t.integer :contactNumber
      t.text :address
      t.string :emailId
      t.boolean :registered

      t.timestamps
    end
  end

  def self.down
    drop_table :users
  end
end
