package com.voterid.imagepload.pojo;

public class UpdatePojo {
        private String[] result;

        private String status;

        public String[] getResult ()
        {
            return result;
        }

        public void setResult (String[] result)
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

}
