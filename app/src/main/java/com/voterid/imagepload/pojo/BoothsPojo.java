package com.voterid.imagepload.pojo;

public class BoothsPojo {
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

        private String updated_at;


        private String created_at;

        private String booth_number;


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

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getBooth_number ()
        {
            return booth_number;
        }

        public void setBooth_number (String booth_number)
        {
            this.booth_number = booth_number;
        }

    }
}
