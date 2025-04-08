pipeline {
	agent any

	stages {
		stage('Checkout') {
			steps {
				git url: 'https://github.com/Yeoun-project/yeoun.git', branch: 'develop'
            }
		}

		stage('Build And Deploy') {
			parallel {
				stage('Frontend') {
					stages {
					    stage('Frontend Generate Env') {
					        steps {
					            withCredentials([
					                string(credentialsId: 'yeoun-front-vite-api-base-url', variable: 'VITE_API_BASE_URL'),
					                string(credentialsId: 'yeoun-front-vite-oauth-google', variable: 'VITE_OAUTH_GOOGLE'),
					                string(credentialsId: 'yeoun-front-vite-oauth-kakao', variable: 'VITE_OAUTH_KAKAO'),
					                string(credentialsId: 'yeoun-front-vite-oauth-naver', variable: 'VITE_OAUTH_NAVER'),
					            ]) {
					                sh '''
                                        cat <<EOF > frontend/.env.production
                                        VITE_API_BASE_URL=$VITE_API_BASE_URL

                                        VITE_OAUTH_GOOGLE=$VITE_OAUTH_GOOGLE
                                        VITE_OAUTH_KAKAO=$VITE_OAUTH_KAKAO
                                        VITE_OAUTH_NAVER=$VITE_OAUTH_NAVER
                                        EOF
					                '''
					            }
					        }
					    }

						stage('Frontend Build') {
							steps {
								sh '''
									docker build \
									-f frontend/Dockerfile \
									-t yeoun-front:latest .
								'''
							}
						}

						stage('Frontend Deploy') {
							steps {
								sh '''
									docker rm -f yeoun-front || true

									docker run -d \
									--name yeoun-front \
									-p 80:80 -p 443:443 \
									-v /etc/letsencrypt:/etc/letsencrypt \
                                    -v /home/ubuntu/certbot:/var/www/certbot \
									yeoun-front:latest
								'''
							}
						}
					}
				}

				stage('Backend') {
					stages {
						stage('Backend Build') {
							steps {
								sh 'docker build -t yeoun-back:latest ./backend'
							}
						}

						stage('Backend Deploy') {
							steps {
								withCredentials([
									string(credentialsId: 'yeoun-back-jwt-secret', variable: 'JWT_SECRET'),
									string(credentialsId: 'yeoun-back-db-host', variable: 'DB_HOST'),
									string(credentialsId: 'yeoun-back-db-port', variable: 'DB_PORT'),
									string(credentialsId: 'yeoun-back-db-password', variable: 'DB_PASSWORD'),
									string(credentialsId: 'yeoun-back-jwt-secret', variable: 'JWT_SECRET'),
									string(credentialsId: 'yeoun-back-oci-compute-host', variable: 'OCI_COMPUTE_HOST'),
									string(credentialsId: 'yeoun-back-google-client-id', variable: 'GOOGLE_CLIENT_ID'),
									string(credentialsId: 'yeoun-back-google-client-secret', variable: 'GOOGLE_SECRET'),
									string(credentialsId: 'yeoun-back-naver-client-id', variable: 'NAVER_CLIENT_ID'),
									string(credentialsId: 'yeoun-back-naver-client-secret', variable: 'NAVER_CLIENT_SECRET'),
									string(credentialsId: 'yeoun-back-kakao-client-id', variable: 'KAKAO_CLIENT_ID'),
									string(credentialsId: 'yeoun-back-keystore-password', variable: 'KEYSTORE_PASSWORD')
								] ){
									sh '''
									docker rm -f yeoun-back || true

									docker run -d \
									--name yeoun-back \
									-p 8080:8080 \
									-e DB_HOST=$DB_HOST \
									-e DB_PORT=$DB_PORT \
									-e DB_PASSWORD=$DB_PASSWORD \
									-e JWT_SECRET=$JWT_SECRET \
									-e OCI_COMPUTE_HOST=$OCI_COMPUTE_HOST \
									-e KEYSTORE_PASSWORD=$KEYSTORE_PASSWORD \
									-e GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID \
									-e GOOGLE_SECRET=$GOOGLE_SECRET \
									-e NAVER_CLIENT_ID=$NAVER_CLIENT_ID \
									-e NAVER_CLIENT_SECRET=$NAVER_CLIENT_SECRET \
									-e KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
									-e SPRING_PROFILES_ACTIVE=prod \
									yeoun-back:latest
								'''
								}
							}
						}
					}
				}
			}
		}
	}
}
