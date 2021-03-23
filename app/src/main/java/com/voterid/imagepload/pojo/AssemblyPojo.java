package com.voterid.imagepload.pojo;

public class AssemblyPojo {
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
        private String assembly_constituency_name;

        private String revenue_district;

        private String updated_at;

        private String assembly_constituency_number;

        private String assembly_constituency_id;

        private String created_at;

        private String state;

        private String assembly_constituency;

        public String getAssembly_constituency_name ()
        {
            return assembly_constituency_name;
        }

        public void setAssembly_constituency_name (String assembly_constituency_name)
        {
            this.assembly_constituency_name = assembly_constituency_name;
        }

        public String getRevenue_district ()
        {
            return revenue_district;
        }

        public void setRevenue_district (String revenue_district)
        {
            this.revenue_district = revenue_district;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getAssembly_constituency_number ()
        {
            return assembly_constituency_number;
        }

        public void setAssembly_constituency_number (String assembly_constituency_number)
        {
            this.assembly_constituency_number = assembly_constituency_number;
        }

        public String getAssembly_constituency_id ()
        {
            return assembly_constituency_id;
        }

        public void setAssembly_constituency_id (String assembly_constituency_id)
        {
            this.assembly_constituency_id = assembly_constituency_id;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getState ()
        {
            return state;
        }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getAssembly_constituency ()
        {
            return assembly_constituency;
        }

        public void setAssembly_constituency (String assembly_constituency)
        {
            this.assembly_constituency = assembly_constituency;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [assembly_constituency_name = "+assembly_constituency_name+", revenue_district = "+revenue_district+", updated_at = "+updated_at+", assembly_constituency_number = "+assembly_constituency_number+", assembly_constituency_id = "+assembly_constituency_id+", created_at = "+created_at+", state = "+state+", assembly_constituency = "+assembly_constituency+"]";
        }
    }



}
