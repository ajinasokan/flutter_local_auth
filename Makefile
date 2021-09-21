TAG=local_auth-v1.1.8

pull-upstream:
	find . -maxdepth 1 -not -name ".git" -not -name ".gitignore" -not -name "Makefile" -exec rm -rf {} \;
	curl -L https://github.com/flutter/plugins/archive/refs/tags/$(TAG).zip > local_auth.zip
	unzip local_auth.zip "plugins-$(TAG)/packages/local_auth/*"
	mv plugins-$(TAG)/packages/local_auth/* .
	rm -rf plugins-$(TAG)
	rm -f local_auth.zip
	