import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import './ResendToken.css';
import { useNavigate } from "react-router-dom";
import { SendResentTokent } from "../ActionCreator/UserActionCreator";
const ResendToken = () => {

    const navigate = useNavigate();

  return (
    <Formik
      initialValues={{ email: '' }}
      validationSchema={Yup.object({
        email: Yup.string().email('Invalid email address').required('Email is required'),
      })}
      onSubmit={(email) => {

        SendResentTokent(navigate,email);

    }}
    >
      {({ errors, touched }) => (
       <div className="container m-auto mt-5 p-5">
         <Form>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <Field
              type="email"
              id="email"
              name="email"
              className={`form-control ${errors.email && touched.email ? 'is-invalid' : ''}`}
            />
            <ErrorMessage name="email" component="span" className="ErrorMsg" />
          </div>

          <div className="d-flex justify-content-end w-100">
            <button type="submit" className="btn btn-primary mt-5">
              Resend Token
            </button>
          </div>
        </Form>
       </div>
      )}
    </Formik>
  );
};

export default ResendToken;
