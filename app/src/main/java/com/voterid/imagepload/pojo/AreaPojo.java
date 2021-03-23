package com.voterid.imagepload.pojo;

public class AreaPojo {
    private Result[] result;

    private String status;

    public Result[] getResult ()
    {
        return result;
    }

    public void setResult (Result[] result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", status = "+status+"]";
    }
    public class Result
    {
        private String booth_id;

        private String booth_name;

        private String area_name;

        private String pincode;

        private String updated_at;

        private String ward_number;

        private String created_at;



        private String area_id;

        private String area_number;

        public String getBooth_id ()
        {
            return booth_id;
        }

        public void setBooth_id (String booth_id)
        {
            this.booth_id = booth_id;
        }

        public String getBooth_name ()
        {
            return booth_name;
        }

        public void setBooth_name (String booth_name)
        {
            this.booth_name = booth_name;
        }

        public String getArea_name ()
        {
            return area_name;
        }

        public void setArea_name (String area_name)
        {
            this.area_name = area_name;
        }



        public String getPincode ()
        {
            return pincode;
        }

        public void setPincode (String pincode)
        {
            this.pincode = pincode;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getWard_number ()
        {
            return ward_number;
        }

        public void setWard_number (String ward_number)
        {
            this.ward_number = ward_number;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }


        public String getArea_id ()
        {
            return area_id;
        }

        public void setArea_id (String area_id)
        {
            this.area_id = area_id;
        }

        public String getArea_number ()
        {
            return area_number;
        }

        public void setArea_number (String area_number)
        {
            this.area_number = area_number;
        }


    }


}
