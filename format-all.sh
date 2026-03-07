#!/bin/bash

echo "🧼 Cleaning up and formatting all SalesMaster modules..."

# List of your modules
MODULES=("discovery-server" "api-gateway" "identity-service" "order-service" "inventory-service" "customer-service")

for module in "${MODULES[@]}"; do
    if [ -d "$module" ]; then
        echo "✨ Formatting $module..."
        cd "$module" && ../mvnw spotless:apply && cd ..
    else
        echo "⚠️  Directory $module not found, skipping."
    fi
done

echo "✅ Project is now sparkling clean!"
